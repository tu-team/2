using System;
using Autofac;

namespace TU.Infrastructure.Persistence
{
    internal abstract class AutofacScope : IDisposable
    {
        private readonly ILifetimeScope _scope;

        protected ILifetimeScope Scope
        {
            get { return _scope; }
        }

        protected AutofacScope(ILifetimeScope scope)
        {
            _scope = scope.BeginLifetimeScope();
        }

        #region Implementation of IDisposable

        public void Dispose()
        {
            Scope.Dispose();
        }

        #endregion
    }
}