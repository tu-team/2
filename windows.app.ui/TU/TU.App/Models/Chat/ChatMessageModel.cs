﻿namespace TU.App.Models.Chat
{
    using System;
    using System.Globalization;
    using Caliburn.Micro;
    using Interfaces;

    public class ChatMessageModel : PropertyChangedBase, IChatMessage
    {
        #region Private Members

        private string _message;
        private DateTime _time;
        private UserType _user;

        #endregion

        #region Implementation of IChatMessage

        public virtual UserType User
        {
            get { return _user; }
            set
            {
                _user = value; 
                NotifyOfPropertyChange();
                NotifyOfPropertyChange(nameof(Status));
            }
        }

        public virtual string Message
        {
            get { return _message; }
            set
            {
                _message = value; 
                NotifyOfPropertyChange();
            }
        }

        public virtual DateTime Time
        {
            get { return _time; }
            set
            {
                _time = value;
                NotifyOfPropertyChange();
                NotifyOfPropertyChange(nameof(Status));
            }
        }

        public string Status => $"{User}, {Time.ToString("F", CultureInfo.InvariantCulture)}";

        #endregion
    }
}