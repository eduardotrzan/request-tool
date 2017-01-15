package com.request.tool.model.utils;

import java.util.ArrayList;
import java.util.List;

import com.request.tool.model.configuration.GitLabel;
import com.request.tool.model.lib.ws.LabelWS;

public final class WSMapperUtils {
	
	private WSMapperUtils() {}
	
	public static final LabelWS toLabelWS(GitLabel gitLabel) {
		LabelWS label = new LabelWS();
		label.setName(gitLabel.getName());
		label.setColor(gitLabel.getColor());
		return label;
	}
	
	public static final List<LabelWS> toLabelWSs(List<GitLabel> gitLabels) {
		List<LabelWS> labels = new ArrayList<>();
		for (GitLabel gitLabel : gitLabels) {
			labels.add(toLabelWS(gitLabel));
		}
		return labels;
	}

}