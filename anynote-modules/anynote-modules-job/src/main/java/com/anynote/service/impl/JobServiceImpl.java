package com.anynote.service.impl;

import com.anynote.service.JobService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class JobServiceImpl implements JobService {

    @Resource
    private RestTemplate restTemplate;




}
