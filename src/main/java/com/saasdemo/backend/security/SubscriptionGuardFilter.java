package com.saasdemo.backend.security;

import com.saasdemo.backend.entity.Subscription;
import jakarta.servlet.*;
import com.saasdemo.backend.repository.SubscriptionRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubscriptionGuardFilter implements Filter {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Long orgId = TenantContext.getCurrentTenantId();

        if (orgId != null) {
            Optional<Subscription> subscription = subscriptionRepository.findSubscriptionByCommune(orgId);

            if (subscription.isEmpty()) {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendError(HttpServletResponse.SC_PAYMENT_REQUIRED, "Abonnement inactif ou expiré");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
