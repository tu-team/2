using Autofac;
using TU.Domain.Queries;

namespace TU.Infrastructure.Persistence.Queries
{
    internal class AutofacQueryBuilder : IQueryBuilder
    {
        private readonly ILifetimeScope _scope;

        private ILifetimeScope Scope
        {
            get { return _scope; }
        }

        public AutofacQueryBuilder(ILifetimeScope scope)
        {
            _scope = scope;
        }

        #region Implementation of IQueryBuilder

        public IQueryFor<TResult> For<TResult>()
        {
            return new AutofacQueryFor<TResult>(Scope);
        }

        #endregion
    }
}