package com.prince.movieezapi.api.models.shared;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private int page;
    private int total_pages;
    private int total_results;
    private List<T> results;
}
