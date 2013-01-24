using Autofac;
using TU.Domain.Queries;

namespace TU.Infrastructure.Persistence.Queries
{
    internal class AutofacQueryFor<TResult> : IQueryFor<TResult>
    {
        private readonly ILifetimeScope _scope;

        public ILifetimeScope Scope
        {
            get { return _scope; }
        }

        public AutofacQueryFor(ILifetimeScope scope)
        {
            _scope = scope;
        }

        #region Implementation of IQueryFor<out TResult>

        public TResult With<TCriterion>(TCriterion criterion) where TCriterion : ICriterion
        {
            return Scope.Resolve<IQuery<TCriterion, TResult>>().Execute(criterion);
        }

        #endregion
    }
}