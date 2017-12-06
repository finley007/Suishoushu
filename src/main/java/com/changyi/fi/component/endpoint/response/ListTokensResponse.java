package com.changyi.fi.component.endpoint.response;

import com.changyi.fi.core.response.NormalResponse;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.vo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTokensResponse extends NormalResponse {

    public ListTokensResponse(List<Token> tokenList) {
        this.tokenList = new ArrayList<Map<String, Session>>();
        if (tokenList != null && tokenList.size() > 0) {
            for (Token token : tokenList) {
                Map<String, Session> map = new HashMap<String, Session>();
                map.put(token.getKey(), token.getSession());
                this.tokenList.add(map);
            }
        }
    }

    public List<Map<String, Session>> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Map<String, Session>> tokenList) {
        this.tokenList = tokenList;
    }

    private List<Map<String, Session>> tokenList;

}
