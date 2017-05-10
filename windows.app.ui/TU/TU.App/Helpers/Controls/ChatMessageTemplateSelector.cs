namespace TU.App.Helpers.Controls
{
    using Windows.UI.Xaml;
    using Windows.UI.Xaml.Controls;

    using Models.Chat;

    public class ChatMessageTemplateSelector : DataTemplateSelector
    {
        /// <summary>
        /// Gets or sets the response chat message template.
        /// </summary>
        /// <value>
        /// The response chat message template.
        /// </value>
        public DataTemplate ResponseChatMessageTemplate { get; set; }

        /// <summary>
        /// Gets or sets the request chat message template.
        /// </summary>
        /// <value>
        /// The request chat message template.
        /// </value>
        public DataTemplate RequestChatMessageTemplate { get; set; }

        /// <summary>
        /// When implemented by a derived class, returns a specific Windows.UI.Xaml.DataTemplate for a given item or container.
        /// </summary>
        /// <param name="item">The item to return a template for.</param>
        /// <param name="container">The parent container for the templated item.</param>
        /// <returns>
        /// The template to use for the given item and/or container.
        /// </returns>
        protected override DataTemplate SelectTemplateCore(object item, DependencyObject container)
        {
            if (item is ResponseChatMessageModel)
                return ResponseChatMessageTemplate;
            if (item is RequestChatMessageModel)
                return RequestChatMessageTemplate;

            return base.SelectTemplateCore(item, container);
        }
    }
}