package com.vgorash.web.sd;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Collections;

@Singleton
@Startup
public class ServiceDiscoveryWorkerImpl implements ServiceDiscoveryWorker {
    private Consul client = null;
    private static final String serviceId = "MainTicketService";

    @PostConstruct
    private void register(){
        try {
            client = Consul.builder().build();
            Registration service = ImmutableRegistration.builder()
                    .id(serviceId)
                    .name("main-ticket-service")
                    .port(25443)
                    .check(Registration.RegCheck.ttl(30L))
                    .meta(Collections.singletonMap("app", "soa_lab1-snapshot"))
                    .build();

            client.agentClient().register(service);
        } catch (Exception e) {
            System.err.println("Consul is unavailable");
        }
    }

    @PreDestroy
    private void unregister(){
        try {
            client.agentClient().deregister(serviceId);
        } catch (Exception e) {
            System.err.println("Consul is unavailable");
        }
    }

    @SneakyThrows
    @Schedule(hour = "*", minute = "*", second = "*/15")
    private void checkIn() {
        AgentClient agentClient = client.agentClient();
        agentClient.pass(serviceId);
    }

}
