# product-service for Marcus

This is the main service that will allow as to retrieve different product:

- By category.
- By ids
- And many other functions that will allow us to build a filtered search

We'll also be having user related actions as purchases, customizations, etc...

- Authentication done through tokenization
- Auditing purchases per user
- Simple personalization

Also, we will be creating some endpoint public, as the page might also be used for non-registered users.

In summary, this services allows us to build a similar e-commerce behavior, maybe not fully but at least
the majority of it.

Technologies employed in the service:
JDK 21
Spring Boot 3.4.5+

- Security (JWT), JPA, etc...

Swagger to allow an API view

- These will be both showing the public ones, and the authenticated ones.

Docker

- Postgres compose to have a local storing db

Flyway

- To keep the versions as the project grows, within commits you'll see that I've started with only some entities, but
  afterward it did grow for each needed solution.

Thanks for taking your time to review the project!

Cosmin Constantin, Chirila.

# ADMIN Credentials

Email set in flyway V3.
Password should be: adminPassword123.