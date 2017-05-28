namespace TU.App.Helpers.Converters
{
    using System;
    using Windows.UI;
    using Windows.UI.Xaml.Data;
    using Windows.UI.Xaml.Media;
    using Library.Models;
    using Models.Chat.Interfaces;

    public class MessageTypeToColorConverter : IValueConverter
    {
        #region Implementation of IValueConverter

        public object Convert(object value, Type targetType, object parameter, string language)
        {
            var message = value as IChatMessage;

            if (message?.Type == RequestType.Train)
            {
                switch (message.User)
                {
                    case UserType.User:
                        return new SolidColorBrush(Colors.CornflowerBlue);
                    case UserType.TU:
                        return new SolidColorBrush(Colors.LightGray);
                }
            }
            else if (message?.Type == RequestType.Request)
            {
                switch (message.User)
                {
                    case UserType.User:
                        return new SolidColorBrush(Colors.LightSteelBlue);
                    case UserType.TU:
                        return new SolidColorBrush(Colors.Snow);
                }
            }

            return new SolidColorBrush(Colors.CornflowerBlue);
        }

        public object ConvertBack(object value, Type targetType, object parameter, string language)
        {
            throw new NotImplementedException();
        }

        #endregion
    }
}