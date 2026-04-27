#!/bin/sh
set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
NACOS_ADDR="${NACOS_ADDR:-http://nacos:8848}"
NACOS_NAMESPACE="${NACOS_NAMESPACE:-0587fa28-1301-43db-a7a1-599c00fc3f70}"
NACOS_GROUP="${NACOS_GROUP:-DEFAULT_GROUP}"
CONFIG_DIR="${NACOS_CONFIG_DIR:-${SCRIPT_DIR}/configs}"
MAX_RETRY="${NACOS_INIT_MAX_RETRY:-60}"
SLEEP_SECONDS="${NACOS_INIT_SLEEP_SECONDS:-2}"
ACCESS_TOKEN=""

retry_count=0
until curl -fsS "${NACOS_ADDR}/nacos/v1/console/health/readiness" >/dev/null; do
    retry_count=$((retry_count + 1))
    if [ "$retry_count" -ge "$MAX_RETRY" ]; then
        echo "Nacos is not ready after ${MAX_RETRY} attempts"
        exit 1
    fi
    echo "Waiting for Nacos readiness (${retry_count}/${MAX_RETRY})"
    sleep "$SLEEP_SECONDS"
done

if [ -n "${NACOS_USERNAME:-}" ] && [ -n "${NACOS_PASSWORD:-}" ]; then
    login_response="$(curl -fsS -X POST "${NACOS_ADDR}/nacos/v1/auth/login" \
        --data-urlencode "username=${NACOS_USERNAME}" \
        --data-urlencode "password=${NACOS_PASSWORD}" || true)"
    ACCESS_TOKEN="$(printf "%s" "$login_response" | sed -n 's/.*"accessToken":"\([^"]*\)".*/\1/p')"
fi

published=0
for config_file in "${CONFIG_DIR}"/*.yml "${CONFIG_DIR}"/*.yaml; do
    [ -e "$config_file" ] || continue
    data_id="$(basename "$config_file")"
    query="dataId=${data_id}&group=${NACOS_GROUP}&type=yaml"

    if [ -n "$NACOS_NAMESPACE" ]; then
        query="${query}&tenant=${NACOS_NAMESPACE}"
    fi

    if [ -n "$ACCESS_TOKEN" ]; then
        query="${query}&accessToken=${ACCESS_TOKEN}"
    fi

    echo "Publishing ${data_id} to Nacos"
    curl -fsS -X POST "${NACOS_ADDR}/nacos/v1/cs/configs?${query}" \
        --data-urlencode "content@${config_file}" >/dev/null
    published=$((published + 1))
done

if [ "$published" -eq 0 ]; then
    echo "No Nacos config files found in ${CONFIG_DIR}"
else
    echo "Published ${published} Nacos config file(s)"
fi
