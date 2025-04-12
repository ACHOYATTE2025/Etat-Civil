package com.saasdemo.backend.security;

/*@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter implements Filter{
  private final JwtUtil jwtUtil;
  private final UtilisateurRepository utilisateurRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
          

        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = jwtUtil.extractAllClaims(token);
                String email = claims.getSubject();
                Long communeId = claims.get("communeId", Long.class);

                this.utilisateurRepository.findByEmail(email).ifPresent(user -> {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    TenantContext.setCurrentTenant(communeId); // multi-tenant context
                });
            } catch (Exception ex) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear() ;// évite les fuites de thread}

     
    }


  }
}
*/

    
