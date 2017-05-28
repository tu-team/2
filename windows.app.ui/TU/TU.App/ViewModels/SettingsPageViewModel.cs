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
        /// Gets or sets the train service address.
        /// </summary>
        /// <value>
        /// The train service address.
        /// </value>
        public string TrainServiceAddress
        {
            get { return _settings.TrainServiceAddress; }
            set
            {
                _settings.TrainServiceAddress = value;
                NotifyOfPropertyChange();
            }
        }

        /// <summary>
        /// Gets or sets the request service address.
        /// </summary>
        /// <value>
        /// The request service address.
        /// </value>
        public string RequestServiceAddress
        {
            get
            {
                return _settings.RequestServiceAddress;
            }
            set
            {
                _settings.RequestServiceAddress = value;
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