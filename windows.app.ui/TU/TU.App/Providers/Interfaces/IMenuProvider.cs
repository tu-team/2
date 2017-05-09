namespace TU.App.Providers.Interfaces
{
    using System.Collections.Generic;

    using Models;

    public interface IMenuProvider
    {
        IEnumerable<MenuItem> GetMainItems();
        IEnumerable<MenuItem> GetOptionItems();
    }
}