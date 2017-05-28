namespace TU.App.ViewModels
{
    using System;
    using System.Linq;
    using Caliburn.Micro;
    using Helpers;
    using Library.Models;
    using Library.Providers.Interfaces;

    using Models.Chat;
    using Models.Chat.Interfaces;

    public class ChatPageViewModel : Screen
    {
        #region Private Members

        private readonly ITuServiceProvider _tuServiceProvider;

        private BindableCollection<IChatMessage> _messages = new BindableCollection<IChatMessage>();
        
        private bool _canSendRequest = true;
        private string _requestMessage;
        private RequestType _selectedRequestType = RequestType.Request;

        #endregion

        /// <summary>
        /// Gets or sets a value indicating whether this instance can send request.
        /// </summary>
        /// <value>
        ///   <c>true</c> if this instance can send request; otherwise, <c>false</c>.
        /// </value>
        public bool CanSendRequest
        {
            get { return _canSendRequest; }
            set
            {
                _canSendRequest = value;
                NotifyOfPropertyChange();
            }
        }
        
        /// <summary>
        /// Gets or sets the request message.
        /// </summary>
        /// <value>
        /// The request message.
        /// </value>
        public string RequestMessage
        {
            get { return _requestMessage; }
            set
            {
                _requestMessage = value; 
                NotifyOfPropertyChange();
            }
        }

        /// <summary>
        /// Gets or sets the type of the selected message.
        /// </summary>
        /// <value>
        /// The type of the selected message.
        /// </value>
        public RequestType SelectedRequestType
        {
            get { return _selectedRequestType; }
            set
            {
                _selectedRequestType = value;
                NotifyOfPropertyChange();
            }
        }

        /// <summary>
        /// Gets or sets the messages.
        /// </summary>
        /// <value>
        /// The messages.
        /// </value>
        public BindableCollection<IChatMessage> Messages
        {
            get { return _messages; }
            set
            {
                _messages = value; 
                NotifyOfPropertyChange();
            }
        }

        /// <summary>
        /// Gets the messages types.
        /// </summary>
        /// <value>
        /// The messages types.
        /// </value>
        public BindableCollection<RequestType> MessagesTypes => Enum.GetValues(typeof(RequestType)).Cast<RequestType>().ToBindableCollection();

        /// <summary>
        /// Initializes a new instance of the <see cref="ChatPageViewModel"/> class.
        /// </summary>
        /// <param name="tuServiceProvider">The tu service provider.</param>
        public ChatPageViewModel(ITuServiceProvider tuServiceProvider)
        {
            _tuServiceProvider = tuServiceProvider;

            NotifyProperties();
        }

        /// <summary>
        /// Notifies the properties.
        /// </summary>
        private void NotifyProperties()
        {
            NotifyOfPropertyChange(nameof(MessagesTypes));
            NotifyOfPropertyChange(nameof(SelectedRequestType));
        }

        /// <summary>
        /// Sends the request.
        /// </summary>
        public async void SendRequest()
        {
            CanSendRequest = false;

            var requestChatMessage = new RequestChatMessageModel()
            {
                Message = RequestMessage,
                User = UserType.User,
                Time = DateTime.Now,
                Type = SelectedRequestType
            };

            var request = new RequestModel()
            {
                Channel = 0,
                Data = new RequestDataModel()
                {
                    Data = RequestMessage,
                    Timestamp = DateTime.Now
                },
                Type = SelectedRequestType
            };

            RequestMessage = string.Empty;

            _messages.Add(requestChatMessage);

            var responseChatMessage = new ResponseChatMessageModel() {User = UserType.TU, Type = SelectedRequestType};
            Messages.Add(responseChatMessage);

            var response = await _tuServiceProvider.SendRequest(request);
            UpdateResponseMessage(response, responseChatMessage);

            CanSendRequest = true;
        }

        /// <summary>
        /// Updates the response message.
        /// </summary>
        /// <param name="response">The response.</param>
        /// <param name="responseChatMessage">The response chat message.</param>
        private void UpdateResponseMessage(ResponseModel response, ResponseChatMessageModel responseChatMessage)
        {
            responseChatMessage.Message = response.Data;
            responseChatMessage.User = UserType.TU;
            responseChatMessage.IsResponseReceived = true;
            responseChatMessage.Time = DateTime.Now;
        }
    }
}