namespace TU.App.Library.Models
{
    public class SettingsModel 
    {
        /// <summary>
        /// Gets or sets the service address.
        /// </summary>
        /// <value>
        /// The service address.
        /// </value>
        public string TrainServiceAddress
        {
            get { return Windows.Storage.ApplicationData.Current.LocalSettings.Values["TrainServiceAddress"] as string ?? "http://localhost:9000/train"; }
            set { Windows.Storage.ApplicationData.Current.LocalSettings.Values["TrainServiceAddress"] = value; }
        }

        /// <summary>
        /// Gets or sets the request service address.
        /// </summary>
        /// <value>
        /// The request service address.
        /// </value>
        public string RequestServiceAddress
        {
            get { return Windows.Storage.ApplicationData.Current.LocalSettings.Values["RequestServiceAddress"] as string ?? "http://localhost:9000/request"; }
            set { Windows.Storage.ApplicationData.Current.LocalSettings.Values["RequestServiceAddress"] = value; }
        }
    }
}