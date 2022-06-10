package shadow.practice.portfolio.Audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

//bean implementation -> Prototype Style.
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        /**
         * @returns the current authenticated logged-in user.
         */
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
