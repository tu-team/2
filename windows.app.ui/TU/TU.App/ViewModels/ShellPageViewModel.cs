namespace TU.App.ViewModels
{
    using Windows.UI.Xaml.Controls;

    using Caliburn.Micro;

    using Models;
    using Helpers;
    using Providers.Interfaces;

    public class ShellPageViewModel : Screen
    {
        #region Private Members

        private readonly IEventAggregator _eventAggregator;
        private readonly WinRTContainer _container;
        private readonly IMenuProvider _menuProvider;

        private INavigationService _navigation;

        private bool _paneOpen;

        #endregion

        #region Menu

        /// <summary>
        /// Gets the main menu items.
        /// </summary>
        /// <value>
        /// The main menu items.
        /// </value>
        public BindableCollection<MenuItemModel> MainMenu => _menuProvider.GetMainItems().ToBindableCollection();
        
        /// <summary>
        /// Gets the options menu items.
        /// </summary>
        /// <value>
        /// The options menu items.
        /// </value>
        public BindableCollection<MenuItemModel> OptionsMenu => _menuProvider.GetOptionItems().ToBindableCollection();

        /// <summary>
        /// Gets or sets a value indicating whether pane is open.
        /// </summary>
        /// <value>
        ///   <c>true</c> if pane is open; otherwise, <c>false</c>.
        /// </value>
        public bool PaneOpen
        {
            get { return _paneOpen; }
            set
            {
                _paneOpen = value;
                NotifyOfPropertyChange();
            }
        }
        

        /// <summary>
        /// Mains the menu item click.
        /// </summary>
        /// <param name="sender">The sender.</param>
        /// <param name="eventArgs">The <see cref="ItemClickEventArgs"/> instance containing the event data.</param>
        private void MainMenuItemClick(object sender, ItemClickEventArgs eventArgs)
        {
            var menuItem = eventArgs.ClickedItem as MenuItemModel;

            if (menuItem.Page == typeof(HomePageViewModel))
                _navigation.For<HomePageViewModel>().Navigate();
            else if(menuItem.Page == typeof(ChatPageViewModel))
                _navigation.For<ChatPageViewModel>().Navigate();
        }

        /// <summary>
        /// Options the menu item click.
        /// </summary>
        /// <param name="sender">The sender.</param>
        /// <param name="eventArgs">The <see cref="ItemClickEventArgs"/> instance containing the event data.</param>
        private void OptionMenuItemClick(object sender, ItemClickEventArgs eventArgs)
        {
            var menuItem = eventArgs.ClickedItem as MenuItemModel;

            if (menuItem.Page == typeof(SettingsPageViewModel))
                _navigation.For<SettingsPageViewModel>().Navigate();
        }

        #endregion

        #region Navigation

        /// <summary>
        /// Setups the navigation.
        /// </summary>
        /// <param name="frame">The frame.</param>
        public void SetupNavigation(Frame frame)
        {
            _navigation = _container.RegisterNavigationService(frame);
            _navigation.BackRequested += (sender, args) =>
            {
                args.Handled = true;

                if (_navigation.CanGoBack)
                    _navigation.GoBack();
                else
                    TryClose();
            };
            
            _navigation.For<HomePageViewModel>().Navigate();
        }

        #endregion

        /// <summary>
        /// Initializes a new instance of the <see cref="ShellPageViewModel"/> class.
        /// </summary>
        /// <param name="container">The container.</param>
        /// <param name="eventAggregator">The event aggregator.</param>
        /// <param name="menuProvider">The menu provider.</param>
        public ShellPageViewModel(WinRTContainer container, IEventAggregator eventAggregator, IMenuProvider menuProvider)
        {
            _eventAggregator = eventAggregator;
            _menuProvider = menuProvider;
            _container = container;

            Initialize();
            NotifyProperties();
        }

        /// <summary>
        /// Initializes this instance.
        /// </summary>
        private void Initialize()
        {
            _eventAggregator.Subscribe(this);
        }

        /// <summary>
        /// Notifies the properties.
        /// </summary>
        private void NotifyProperties()
        {
            NotifyOfPropertyChange(nameof(MainMenu));
            NotifyOfPropertyChange(nameof(OptionsMenu));
        }
    }
}