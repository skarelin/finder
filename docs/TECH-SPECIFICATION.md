Tech specification:

1. Prefix for every table in database: BF_ (business-finder)
2. At this moment in APIs we have two types errors: business (success + errors), request (timestamp + status + errors);
   Later probably we will use only one structure.

- JSON structure for business error:
  { success: true/false, errors: [...]
  }

3. Email is the unique username for every user in application.
4. Spring Security: we are not using @PreAuthorize. Only @Secured is allowed.
5. We have system user in application. See `BfSecurityConfiguration.systemUser()`; Use `private final User systemUser;`
   to inject it.
6. Every API should be documented. Later it's more readable in Swagger.
7. (if mobile application should be updated) Change `bf.application.version` in properties for every deploy to PROD
   environment. (should be CI/CD in future)
8. (not now, after logging implementation) We are using LogBack. We should log every business action.
9. If you want to add the custom business exception, please remember to update `CustomGlobalExceptionHandler`
10. Entity as a response should be mapped to DTO.
11. Command should be one-directional.