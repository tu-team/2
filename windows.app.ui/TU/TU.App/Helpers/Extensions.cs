namespace TU.App.Helpers
{
    using System.Collections.Generic;

    using Caliburn.Micro;

    public static class Extensions
    {
        /// <summary>
        /// To the bindable collection.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="source">The source.</param>
        /// <returns></returns>
        public static BindableCollection<T> ToBindableCollection<T>(this IEnumerable<T> source)
        {
            return new BindableCollection<T>(source);
        }
    }
}