namespace TU.App.Providers.Interfaces
{
    using System.Collections.Generic;

    using Models;

    public interface IMenuProvider
    {
        /// <summary>
        /// Gets the main items.
        /// </summary>
        /// <returns></returns>
        IEnumerable<MenuItemModel> GetMainItems();

        /// <summary>
        /// Gets the option items.
        /// </summary>
        /// <returns></returns>
        IEnumerable<MenuItemModel> GetOptionItems();
    }
}