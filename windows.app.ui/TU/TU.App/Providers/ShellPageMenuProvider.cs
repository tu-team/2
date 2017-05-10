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

        public IEnumerable<MenuItemModel> GetMainItems()
        {
            yield return new MenuItemModel { Name = _resourceLoader.GetString("Home"), Icon = Symbol.Home, Page = typeof(HomePageViewModel) };
            yield return new MenuItemModel { Name = _resourceLoader.GetString("Chat"), Icon = Symbol.Message, Page = typeof(ChatPageViewModel) };
        }

        public IEnumerable<MenuItemModel> GetOptionItems()
        {
            yield return new MenuItemModel { Name = _resourceLoader.GetString("Settings"), Icon = Symbol.Setting, Page = typeof(SettingsPageViewModel) };
        }

        #endregion
    }
}