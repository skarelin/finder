20.12.1996 - project started.

TODO:

1. Add flyway;
2. Correct configuration for database;
3. build.gradle - version should be declared as variables;
4. Pay attention to @Transactional annotation.
5. Expiring user. If subscription is cancelled. Job which making the user expired and unactivated.
6. Countries and cities (probably it will be stored in database)
7. Admin panel. PartnershipProposal entity - add there field about verified.
8. Add swagger.
9. Make sure that every business action has been logged.
10. User is removed. Implement scheduler for removing archive partnership-proposals.
11. Announcements to users.
12. Change Error enum to ErrorCode. It's more readable.
13. Compare Partnership and Investment entities. For example Language enum should be already added!

Mobile app:

1. Announcement
2. Announcement -> update.
3. "Remove user" button should be implemented in application for sure.

Questions:

Form Partnership Proposal:

1. Subject
2. Industry
3. Country
4. City
5. KnowledgeOfProposalCreator
6. Team Available
7. Team Description
8. Additional Description
9. Team language
10. Attachment? (business plan)

Form Investment Proposal:

1. Subject
2. Project Description
3. Country
4. City
5. Team language (optional)
6. Min investment (optional)
7. Project Budget (optional)
8. Payback period (optional)
9. History of company (optional)
10. Attachment? (business plan)
