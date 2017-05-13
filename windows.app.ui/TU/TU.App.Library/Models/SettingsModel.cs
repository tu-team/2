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
        public string ServiceAddress
        {
            get { return Windows.Storage.ApplicationData.Current.LocalSettings.Values["ServiceAddress"] as string ?? "http://localhost:9000/service"; }
            set { Windows.Storage.ApplicationData.Current.LocalSettings.Values["ServiceAddress"] = value; }
        }
    }
}