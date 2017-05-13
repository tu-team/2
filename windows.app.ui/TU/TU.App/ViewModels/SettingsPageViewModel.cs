namespace TU.App.ViewModels
{
    using Caliburn.Micro;

    using Library.Models;

    public class SettingsPageViewModel : Screen
    {
        #region Private Members 

        private readonly SettingsModel _settings;

        #endregion

        /// <summary>
        /// Gets or sets the service address.
        /// </summary>
        /// <value>
        /// The service address.
        /// </value>
        public string ServiceAddress
        {
            get { return _settings.ServiceAddress; }
            set
            {
                _settings.ServiceAddress = value;
                NotifyOfPropertyChange();
            }
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="SettingsPageViewModel"/> class.
        /// </summary>
        /// <param name="settings">The settings.</param>
        public SettingsPageViewModel(SettingsModel settings)
        {
            _settings = settings;
        }
    }
}