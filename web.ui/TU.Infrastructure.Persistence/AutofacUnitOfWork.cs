using Autofac;
using TU.Domain.Entities;
using TU.Domain.Queries;

namespace TU.Infrastructure.Persistence
{
    /// <summary>
    /// An Autofac specific implementation of <see cref="IUnitOfWork"/>.
    /// </summary>
    internal class AutofacUnitOfWork : AutofacScope, IUnitOfWork
    {
        private readonly TuDashboardDbContext _dbContext;
        private readonly IQueryBuilder _queryBuilder;

        private TuDashboardDbContext DbContext
        {
            get { return _dbContext; }
        }

        private IQueryBuilder QueryBuilder
        {
            get { return _queryBuilder; }
        }

        public AutofacUnitOfWork(ILifetimeScope scope)
            : base(scope)
        {
            _dbContext = Scope.Resolve<TuDashboardDbContext>();
            _queryBuilder = Scope.Resolve<IQueryBuilder>();
        }

        #region Implementation of IUnitOfWork

        public TEntity CreateEntity<TEntity>() where TEntity : EntityBase, new()
        {
            var enity = new TEntity();
            DbContext.Set<TEntity>().Add(enity);
            return enity;
        }

        public void DeleteEntity<TEntity>(TEntity entity) where TEntity : EntityBase
        {
            DbContext.Set<TEntity>().Remove(entity);
        }

        public IQueryBuilder BuildQuery()
        {
            return QueryBuilder;
        }

        public void Commit()
        {
            DbContext.SaveChanges();
        }

        #endregion
    }
}