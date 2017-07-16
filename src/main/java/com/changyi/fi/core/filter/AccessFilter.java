package com.changyi.fi.core.filter;

import com.changyi.fi.core.dao.SysDao;
import com.changyi.fi.core.model.SysAccessPO;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.Date;

/**
 * Created by finley on 7/15/17.
 */
@Component
public class AccessFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    private SysDao sysDao;

    @Autowired(required = true)
    public void setSysDao(SysDao sysDao) {
        this.sysDao = sysDao;
    }

    public ContainerRequest filter(ContainerRequest creq) {
        SysAccessPO accessPO = new SysAccessPO();
        accessPO.setIp(request.getRemoteHost());
        accessPO.setPort("" + request.getRemotePort());
        accessPO.setUrl(request.getRequestURL().toString());
        accessPO.setAccessTime(new Date());
        this.sysDao.insert(accessPO);
        return creq;
    }

}
