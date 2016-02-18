package com.sample.androidtv.model;

/**
 * Created by pankaj on 17/2/16.
 */
public class SearchResult {
    private Search[] Search;

    private String totalResults;

    private String Response;

    public Search[] getSearch() {
        return Search;
    }

    public void setSearch(Search[] search) {
        this.Search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String Response) {
        this.Response = Response;
    }

    @Override
    public String toString() {
        return "Class Search Result [IMDB ITEM = " + Search + ", totalResults = " + totalResults + ", Response = " + Response + "]";
    }

}
