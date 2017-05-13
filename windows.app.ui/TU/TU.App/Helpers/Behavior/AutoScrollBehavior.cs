namespace TU.App.Helpers.Behavior
{
    using System;

    using Microsoft.Xaml.Interactivity;

    using Windows.UI.Xaml.Controls;

    public class AutoScrollBehavior : Behavior<ScrollViewer>
    {
        #region Private Members

        private double _height = 0.0d;
        private ScrollViewer _scrollViewer = null;

        #endregion

        #region Overrides of Behavior<ScrollViewer>

        /// <summary>
        /// Called after the behavior is attached to the <see cref="P:Microsoft.Xaml.Interactivity.Behavior.AssociatedObject" />.
        /// </summary>
        /// <remarks>
        /// Override this to hook up functionality to the <see cref="P:Microsoft.Xaml.Interactivity.Behavior.AssociatedObject" />
        /// </remarks>
        protected override void OnAttached()
        {
            base.OnAttached();

            _scrollViewer = AssociatedObject;
            _scrollViewer.LayoutUpdated += ScrollViewerOnLayoutUpdated;

        }

        /// <summary>
        /// Called when the behavior is being detached from its <see cref="P:Microsoft.Xaml.Interactivity.Behavior.AssociatedObject" />.
        /// </summary>
        /// <remarks>
        /// Override this to unhook functionality from the <see cref="P:Microsoft.Xaml.Interactivity.Behavior.AssociatedObject" />
        /// </remarks>
        protected override void OnDetaching()
        {
            base.OnDetaching();

            if (_scrollViewer != null)
                _scrollViewer.LayoutUpdated -= ScrollViewerOnLayoutUpdated;
        }

        #endregion

        /// <summary>
        /// Scrolls the viewer on layout updated.
        /// </summary>
        /// <param name="sender">The sender.</param>
        /// <param name="o">The o.</param>
        private void ScrollViewerOnLayoutUpdated(object sender, object o)
        {
            if (Math.Abs(_scrollViewer.ExtentHeight - _height) > 1)
            {
                _scrollViewer.ChangeView(null, _scrollViewer.ExtentHeight, null);
                _height = _scrollViewer.ExtentHeight;
            }
        }
    }
}