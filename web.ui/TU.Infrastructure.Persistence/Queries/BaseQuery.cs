namespace TU.Infrastructure.Persistence.Queries
{
    /// <summary>
    /// Represents a base class for all Query descendant classes, providing the reference to the <see cref="DbContext"/> property.
    /// </summary>
    internal abstract class BaseQuery
    {
        private readonly TuDashboardDbContext _dbContext;

        /// <summary>
        /// Gets a <see cref="NetInspectDbContext"/> instance to perform a query.
        /// </summary>
        protected TuDashboardDbContext DbContext
        {
            get { return _dbContext; }
        }

        protected BaseQuery(TuDashboardDbContext dbContext)
        {
            _dbContext = dbContext;
        }
    }
}