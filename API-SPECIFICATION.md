{host}/


POST: /register

POST: /login

===============================[User-Controller]
PUT: /user -> update user;

DELETE: /user -> delete user;

GET: /user/{optional: userId} -> get user info (profileUrl, profile description,
list of partnership proposals, list of invest proposals, isOwner) -> for current user and foreign user;

==============================[Partnership-Controller]
POST: /partnership-proposal

GET: /partnership-proposal/{partnership-proposal-uuid} -> show proposal by UUID;

GET: /partnership-proposal/all

PUT: /partnership-proposal/{partnership-proposal-uuid} -> update given partnership-proposal-uuid

DELETE: /partnership-proposal/{partnership-proposal-uuid} -> delete proposal by UUID;

PartnershipProposalEntity(WIP):
- subject;
- description;
- country;
- city;
- industry;
- teamLanguage(optional);
- projectBudget(optional);

=============================[INVESTMENT-Controller]
POST: /investment-proposal

GET: /investment-proposal/{investment-proposal-uuid} -> show proposal by UUID;

GET: /investment-proposal/all?country="Poland"&language="En" -> show all investment proposals by filters;

PUT: /investment-proposal/{investment-proposal-uuid} -> update given investment-proposal-uuid

DELETE: /investment-proposal/{investment-proposal-uuid} -> delete investment by UUID;

InvestmentProposalEntity(WIP):
- subject(must have);
- description(optional);
- minimumInvestmentValue(optional);
- country(must have);
- city(optional);
- industry(must have);
- teamLanguage(optional);
- projectBudget(optional);
- expectedPaybackPeriod(optional);