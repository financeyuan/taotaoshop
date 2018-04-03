package com.yaoyao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.yaoyao.common.pojo.SearchResult;

public interface SearchDao {
	
	SearchResult search(SolrQuery query) throws Exception;
}
