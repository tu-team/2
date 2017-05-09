namespace TU.App
{
    using System;
    using System.Collections.Generic;

    using Windows.ApplicationModel.Activation;
    using Windows.UI.Core;

    using Caliburn.Micro;
    using Library.Providers;
    using Library.Providers.Interfaces;
    using Providers;
    using Providers.Interfaces;
    using ViewModels;

    /// <summary>
    /// Provides Caliburn.Micro framework behavior of Application class
    /// </summary>
    sealed partial class App
    {
        #region Private Members

        /// <summary>
        /// Application IoC container
        /// </summary>
        private WinRTContainer _container;

        #endregion

        /// <summary>
        /// Constructor 
        /// Used for for setup Caliburn.Micro framework
        /// </summary>
        public App()
        {
            InitializeComponent();
        }

        #region Overrides of CaliburnApplication

        /// <summary>
        /// Overrides of Configure method from <see cref="CaliburnApplication"/>
        /// </summary>
        protected override void Configure()
        {
            _container = new WinRTContainer();
            _container.RegisterWinRTServices();

            _container
                .Singleton<IMenuProvider, ShellPageMenuProvider>()
                .Singleton<ITuServiceProvider, TuServiceProvider>()
                .Singleton<ShellPageViewModel>();

            _container
                .PerRequest<HomePageViewModel>()
                .PerRequest<SettingsPageViewModel>();
                
            ConfigureNavigation();
        }

        /// <summary>
        /// Configuring application navigation service
        /// </summary>
        private void ConfigureNavigation()
        {
            var navigation = SystemNavigationManager.GetForCurrentView();
            navigation.AppViewBackButtonVisibility = AppViewBackButtonVisibility.Visible;
        }

        #endregion

        #region Overrides of application life cycle

        /// <summary>
        /// Handling OnLaunched event of application life cycle
        /// </summary>
        /// <param name="args"></param>
        protected override void OnLaunched(LaunchActivatedEventArgs args)
        {
            if (args.PreviousExecutionState == ApplicationExecutionState.Running)
                return;

            DisplayRootViewFor<ShellPageViewModel>();
        }

        #endregion

        #region Overrides of IoC

        /// <summary>
        /// Overrides of GetInstance method from <see cref="IoC"/> class to work with application container
        /// </summary>
        /// <param name="service">Type of getting instance</param>
        /// <param name="key">Key of getting instance</param>
        /// <returns>Instance from application container</returns>
        protected override object GetInstance(Type service, string key)
        {
            return _container.GetInstance(service, key);
        }

        /// <summary>
        /// Overrides of GetAllInstances method from <see cref="IoC"/> class to work with application container
        /// </summary>
        /// <param name="service">Type of getting instances</param>
        /// <returns>List of instances from application container</returns>
        protected override IEnumerable<object> GetAllInstances(Type service)
        {
            return _container.GetAllInstances(service);
        }

        /// <summary>
        /// Overrides of BuildUp method from <see cref="IoC"/> class to work with application container
        /// </summary>
        /// <param name="instance">Instance to build</param>
        protected override void BuildUp(object instance)
        {
            _container.BuildUp(instance);
        }

        #endregion
    }
}
