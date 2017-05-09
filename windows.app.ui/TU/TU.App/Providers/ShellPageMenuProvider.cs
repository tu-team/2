namespace TU.App.Providers
{
    using System.Collections.Generic;
    
    using Windows.UI.Xaml.Controls;
    using Windows.ApplicationModel.Resources;
    
    using Models;
    using Interfaces;
    using ViewModels;

    public class ShellPageMenuProvider : IMenuProvider
    {
        private readonly ResourceLoader _resourceLoader = new ResourceLoader();

        #region Implementation of IMenuProvider

        public IEnumerable<MenuItem> GetMainItems()
        {
            yield return new MenuItem { Name = _resourceLoader.GetString("Home"), Icon = Symbol.Home, Page = typeof(HomePageViewModel) };
        }

        public IEnumerable<MenuItem> GetOptionItems()
        {
            yield return new MenuItem { Name = _resourceLoader.GetString("Settings"), Icon = Symbol.Setting, Page = typeof(SettingsPageViewModel) };
        }

        #endregion
    }
}