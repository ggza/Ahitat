package gz.rmbgysz.ahitat.internal;

import androidx.core.view.ViewCompat;
import android.widget.TextView;

public interface HasOnViewAttachListener {
    void setOnViewAttachListener(OnViewAttachListener listener);

    interface OnViewAttachListener {
        void onAttach();

        void onDetach();
    }

    /**
     * Helper class to implement {@link HasOnViewAttachListener}.
     * Usual implementation should look like this:
     * <pre>
     * {@code
     * class MyClass extends TextView implements HasOnViewAttachListener {
     *
     *       private HasOnViewAttachListenerDelegate delegate
     *
     *       @Override
     *       public void setOnViewAttachListener(OnViewAttachListener listener) {
     *          if (delegate == null) delegate = new HasOnViewAttachListenerDelegate(this);
     *          delegate.setOnViewAttachListener(listener);
     *       }
     *
     *       @Override
     *       protected void onAttachedToWindow() {
     *          super.onAttachedToWindow();
     *          delegate.onAttachedToWindow();
     *       }
     *
     *       @Override
     *       protected void onDetachedFromWindow() {
     *          super.onDetachedFromWindow();
     *          delegate.onDetachedFromWindow();
     *      }
     *
     *  }
     * }
     * </pre>
     */
    class HasOnViewAttachListenerDelegate {

        private final TextView view;
        private OnViewAttachListener listener;

        public HasOnViewAttachListenerDelegate(TextView view) {
            this.view = view;
        }

        public void setOnViewAttachListener(OnViewAttachListener listener) {
            if (this.listener != null)
                this.listener.onDetach();
            this.listener = listener;
            if (ViewCompat.isAttachedToWindow(view) && listener != null) {
                listener.onAttach();
            }
        }

        public void onAttachedToWindow() {
            if (listener != null) listener.onAttach();
        }

        public void onDetachedFromWindow() {
            if (listener != null) listener.onDetach();
        }

    }
}