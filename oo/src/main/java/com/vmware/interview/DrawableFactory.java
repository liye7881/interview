package com.vmware.interview;

import com.vmware.interview.listener.PostProcessListener;
import com.vmware.interview.listener.PreProcessListener;
import java.lang.reflect.Proxy;

public class DrawableFactory {

  public static Drawable newInstance(String type) {
    Drawable drawable;
    if (type != null) {
      switch (type.toLowerCase()) {
        case "circle":
          drawable = new Circle();
          break;
        case "square":
          drawable = new Square();
          break;
        case "observer":
          drawable = new ObservableDrawable(PreProcessListener.getInstance(), PostProcessListener.getInstance());
          break;
        default:
          drawable = new DefaultDrawable(type);
      }
    } else {
      drawable = (Drawable) Proxy.newProxyInstance(Drawable.class.getClassLoader(), new Class[] {Drawable.class},
          (proxy, method, args) -> {
        DrawUtils.preProcess();

        Object ret = method.invoke(NothingToDraw.getInstance(), args);

        DrawUtils.postProcess();

        return ret;
      });
    }

    return drawable;
  }


}
