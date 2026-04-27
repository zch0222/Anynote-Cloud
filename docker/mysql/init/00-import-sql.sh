#!/bin/sh
set -eu

MYSQL_APP_USER="${MYSQL_APP_USER:-anynote}"
MYSQL_APP_PASSWORD="${MYSQL_APP_PASSWORD:-Anynote*1832}"
MYSQL_DATABASE="${MYSQL_DATABASE:-anynote}"
NACOS_DB_NAME="${NACOS_DB_NAME:-anynote_config}"
XXL_JOB_DB_NAME="${XXL_JOB_DB_NAME:-anynote_xxl_job}"
export MYSQL_PWD="${MYSQL_ROOT_PASSWORD}"

mysql_exec() {
    mysql --protocol=socket -uroot "$@"
}

mysql_exec <<SQL
CREATE DATABASE IF NOT EXISTS \`${MYSQL_DATABASE}\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS \`${NACOS_DB_NAME}\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS \`${XXL_JOB_DB_NAME}\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS '${MYSQL_APP_USER}'@'%' IDENTIFIED BY '${MYSQL_APP_PASSWORD}';
ALTER USER '${MYSQL_APP_USER}'@'%' IDENTIFIED BY '${MYSQL_APP_PASSWORD}';
GRANT ALL PRIVILEGES ON \`${MYSQL_DATABASE}\`.* TO '${MYSQL_APP_USER}'@'%';
FLUSH PRIVILEGES;
SQL

for sql_file in /docker-entrypoint-initdb.d/source/*.sql; do
    [ -e "$sql_file" ] || continue

    case "$(basename "$sql_file")" in
        anynote_config.sql)
            target_db="${NACOS_DB_NAME}"
            ;;
        anynote_xxl_job.sql)
            target_db="${XXL_JOB_DB_NAME}"
            ;;
        *)
            target_db="${MYSQL_DATABASE}"
            ;;
    esac

    echo "Importing $(basename "$sql_file") into ${target_db}"
    mysql_exec "$target_db" < "$sql_file"
done
