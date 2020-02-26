# Notes

- Multitenancy is an architectural pattern which allows you to isolate customers even if they are using the same hardware or software components. Multitenancy has become even more attractive with the widespread adoption of cloud computing.
- Catalog -> Schema -> Table.

- Hibernate provide an implementation of these two interfaces:

  - MultiTenantConnectionProvider – provides connections per tenant.

  - CurrentTenantIdentifierResolver – resolves the tenant identifier to use.
  
# Strategy approaches:

**1. Catalog-based multitenancy** (Separate Database)
| Pros | Cons |
| --- | --- |
| Each customer uses its own database catalog. Therefore, the tenant identifier is the database catalog itself.Since each customer will only be granted access to its own catalog, it’s very easy to achieve customer isolation. More, the data access layer is not even aware of the multitenancy architecture, meaning that the data access code can focus on business requirements only.|The disadvantage of this strategy is that it requires more work on the Ops side: monitoring, replication, backups. However, with automation in place, this problem could be mitigated.|
| This strategy is useful relational database systems like MySQL where there is no distinction between a catalog and a schema.||

<img align="center" height=70% width=70% src="https://vladmihalcea.com/wp-content/uploads/2018/07/MultitenancyDatabaseCatalog-1024x629.png"/>

**2. Schema-based multitenancy** (Separate Schema)
| Pros | Cons |
| --- | --- |
| Each custom uses its own database schema. Therefore, the tenant identifier is the database schema itself.|if schemas are colocated on the same hardware, one tenant which runs a resource-intensive job might incur latency spikes in other tenants.|
| Since each customer will only be granted access to its own schema, it’s very easy to achieve customer isolation. Also, the data access layer is not even aware of the multitenancy architecture, meaning that, just like for catalog-based multitenancy, the data access code can focus on business requirements only.||
| This strategy is useful for relational database systems like PostgreSQL which support multiple schemas per database (catalog). Replication, backing up, and monitoring can be set up on the catalog-level, hence all schemas could benefit from it.||

<img align="center" height=70% width=70% src="https://vladmihalcea.com/wp-content/uploads/2018/07/MultitenancyDatabaseSchema-1024x657.png"/>

**3. Table-based multitenancy** (Partitioned (Discriminator) Data )
| Pros | Cons |
| --- | --- |
| In a table-based multitenancy architecture, multiple customers reside in the same database catalog and/or schema. To provide isolation, a tenant identifier column must be added to all tables that are shared between multiple clients.| While on the Ops side, this strategy requires no additional work, the data access layer needs extra logic to make sure that each customer is allowed to see only its data and to prevent data leaking from one tenant to the other. Also, since multiple customers are stored together, tables and indexes might grow larger, putting pressure on SQL statement performance.|

<img align="center" height=70% width=70% src="https://vladmihalcea.com/wp-content/uploads/2018/07/MultitenancyDatabaseTable-1024x419.png"/>
