namespace TU.App.ViewModels
{
    using Caliburn.Micro;

    using Models.Chat.Interfaces;

    public class ChatPageViewModel : Screen
    {
        public BindableCollection<IChatMessage> Messages { get; set; }
    }
}