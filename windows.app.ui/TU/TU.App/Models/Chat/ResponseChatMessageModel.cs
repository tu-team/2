namespace TU.App.Models.Chat
{
    public class ResponseChatMessageModel : ChatMessageModel
    {
        #region Private Members

        private bool _isResponseReceived;

        #endregion

        /// <summary>
        /// Gets or sets a value indicating whether this instance is response received.
        /// </summary>
        /// <value>
        ///   <c>true</c> if this instance is response received; otherwise, <c>false</c>.
        /// </value>
        public bool IsResponseReceived
        {
            get { return _isResponseReceived; }
            set
            {
                _isResponseReceived = value;
                NotifyOfPropertyChange();
            }
        }
    }
}