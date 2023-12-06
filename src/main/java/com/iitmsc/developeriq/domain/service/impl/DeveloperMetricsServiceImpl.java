package com.iitmsc.developeriq.domain.service.impl;

import com.iitmsc.developeriq.application.transport.request.GithubBaseRequest;
import com.iitmsc.developeriq.application.transport.response.GithubBaseResponse;
import com.iitmsc.developeriq.domain.entity.Metrics;
import com.iitmsc.developeriq.domain.service.DeveloperMetricsService;
import com.iitmsc.developeriq.external.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperMetricsServiceImpl implements DeveloperMetricsService {


    @Autowired
    private MetricRepository metricRepository;

    /**
     * get Developer related metrics
     *
     * @param githubBaseRequest
     * @return
     */
    @Override
    public GithubBaseResponse getDeveloperMetrics(GithubBaseRequest githubBaseRequest) {
        List<Metrics> metricsList = metricRepository.findAllByOwnerAndRepo(githubBaseRequest.getOwner(), githubBaseRequest.getRepo());
        GithubBaseResponse githubBaseResponse = new GithubBaseResponse();
        if (metricsList.size() > 0) {
            Metrics metrics = metricsList.get(0);
            githubBaseResponse.setTotalCommitCount(metrics.getTotalCommits());
            githubBaseResponse.setTotalForkCount(metrics.getTotalForks());
            githubBaseResponse.setTotalIssueCount(metrics.getTotalIssues());
            githubBaseResponse.setTotalPullRequestCount(metrics.getTotalPullRequests());
        } else {
            githubBaseResponse.setTotalCommitCount(0);
            githubBaseResponse.setTotalForkCount(0);
            githubBaseResponse.setTotalIssueCount(0);
            githubBaseResponse.setTotalPullRequestCount(0);
        }
        githubBaseResponse.setResponseHeader(createResponseHeader(githubBaseRequest.getRequestHeader(), "Successfully retrieved metrics"));
        return githubBaseResponse;
    }
}
