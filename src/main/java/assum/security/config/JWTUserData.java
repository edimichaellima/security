package assum.security.config;

import java.util.List;

public record JWTUserData(Long userId, String email, List<String> roles) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private String email;
        private List<String> roles;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public JWTUserData build() {
            return new JWTUserData(userId, email, roles);
        }
    }
}

