package com.web.crawler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextArea;

/**
 * Created by alexsimonian on 5/11/17.
 */
public class Spider {
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	Map<String, Integer> linkFoundCount = new HashMap<>();
	Map<String, Integer> remoteLinkFoundCount = new HashMap<>();
	public static String url;

	public Set<String> search(JTextArea displayResultsTextArea, JTextArea displayLinksTextArea) {
		while (true) {
			if (CrawlerGUI.isNew) {
				linkFoundCount = new HashMap<>();
				remoteLinkFoundCount = new HashMap<>();
				CrawlerGUI.isNew = false;
			}
			if (CrawlerGUI.isCrawl) {
				SpiderLeg leg = new SpiderLeg();
				Set<String> remoteUrls = new HashSet<String>();
				leg.crawl(url);
				for (String link : leg.getLinks()) {
					if (isRemoteUrl(url, link)) {
						if (link.trim().length() > 0)
							remoteUrls.add(link);
					}

				}

				remoteLinkFoundCount.put(url, remoteUrls.size());
				linkFoundCount.put(url, leg.getLinks().size());
				System.out.println(url);
				System.out.println("Found " + remoteUrls.size() + " remote urls on this page.");
				System.out.println("Found " + leg.getLinks().size() + "  urls on this page.");
				displayRemoteResults(displayResultsTextArea);
				displayLinkResults(displayLinksTextArea);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (String string : remoteUrls) {
					try {
						System.out.println(string);
						url = string;
						search(displayResultsTextArea, displayLinksTextArea);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				return remoteUrls;

			} else {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void displayLinkResults(JTextArea displayLinksTextArea) {
		StringBuilder results = new StringBuilder();
		results.append("Remote URL \t\t  #Links Found");

		results.append("\n");

		for (Map.Entry<String, Integer> entry : linkFoundCount.entrySet()) {
			String key = entry.getKey();
			if (key.indexOf("?") > 0) {
				key = key.substring(0, key.indexOf("?"));
			}

			if (key.length() > 30) {
				key = key.substring(0, 30);
			}

			results.append(key + "\t" + entry.getValue());
			results.append("\n");
		}

		displayLinksTextArea.setText(results.toString());

	}

	private void displayRemoteResults(JTextArea displayResultsTextArea) {

		StringBuilder results = new StringBuilder();

		results.append("\n");

		for (Map.Entry<String, Integer> entry : remoteLinkFoundCount.entrySet()) {
			String key = entry.getKey();
			if (key.indexOf("?") > 0) {
				key = key.substring(0, key.indexOf("?"));
			}
			results.append("Found " + entry.getValue() + " remote URLS on " + key);
			results.append("\n");
		}

		displayResultsTextArea.setText(results.toString());

	}

	private boolean isRemoteUrl(String url, String link) {
		if (!getHostName(url).equalsIgnoreCase(getHostName(link))) {
			return true;
		}
		return false;
	}

	public String getHostName(String url) {
		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
		String hostname = uri.getHost();
		if (hostname != null) {
			return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
		}
		return hostname;
	}

	public List<String> getPagesToVisit() {
		return pagesToVisit;
	}

	public void setPagesToVisit(List<String> pagesToVisit) {
		this.pagesToVisit = pagesToVisit;
	}

	public Set<String> getPagesVisited() {
		return pagesVisited;
	}

	public void setPagesVisited(Set<String> pagesVisited) {
		this.pagesVisited = pagesVisited;
	}

}
