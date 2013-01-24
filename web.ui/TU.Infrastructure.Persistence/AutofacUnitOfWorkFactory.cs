using Autofac;
using TU.Domain.Queries;

namespace TU.Infrastructure.Persistence
{
    /// <summary>
    /// An Autofac specific implementation of <see cref="IUnitOfWorkFactory"/>.
    /// </summary>
    internal class AutofacUnitOfWorkFactory : AutofacScope, IUnitOfWorkFactory
    {
        public AutofacUnitOfWorkFactory(ILifetimeScope scope)
            : base(scope)
        { }

        #region Implementation of IUnitOfWorkFactory

        public IUnitOfWork Create()
        {
            return Scope.Resolve<IUnitOfWork>();
        }

        #endregion
    }
}