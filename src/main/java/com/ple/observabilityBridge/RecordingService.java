/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.ple.observabilityBridge;

import com.ple.util.IArrayMap;
import com.ple.util.IMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Should only be used by a single thread. If it is needed in another thread, pass a cloned instance.
 */
public class RecordingService {

  public final List<RecordingHandler> handlers;
  public final List<String> contextList;
  public final List<Long> startTimeList;

  private RecordingService(List<RecordingHandler> handlers, List<String> contextList, List<Long> startTimeList) {
    this.handlers = handlers;
    this.contextList = contextList;
    this.startTimeList = startTimeList;
  }

  public static RecordingService make(RecordingHandler... handlers) {
    return new RecordingService(List.of(handlers), new ArrayList<>(), new ArrayList<>());
  }

  public RecordingService open(String context, IMap<String, String> dimensions) {
    startTimeList.add(System.currentTimeMillis());
    contextList.add(context);

    for (RecordingHandler handler : handlers) {
      handler.open(this, context, dimensions);
    }

    return this;
  }

  public RecordingService open(String context) {
    return open(context, null);
  }

  public RecordingService close(String context, IMap<String, String> dimensions) {
    for (RecordingHandler handler : handlers) {
      handler.close(this, context, dimensions);
    }
    startTimeList.remove(startTimeList.size() - 1);
    contextList.remove(contextList.size() - 1);
    return this;
  }

  public RecordingService close(String context) {
    return close(context, null);
  }

  public RecordingService clone() {
    return new RecordingService(new ArrayList(handlers), new ArrayList(contextList), new ArrayList(startTimeList));
  }

  public RecordingService log(int level, String base, IMap<String, String> dimensions) {
    for (RecordingHandler handler : handlers) {
      handler.log(this, level, base, dimensions);
    }
    return this;
  }

  public RecordingService log(int level, String base, Object... dimensions) {
    return log(level, base, IArrayMap.make(dimensions);
  }

  public RecordingService log(String base, Object... dimensions) {
    return log(0, base, IArrayMap.make(dimensions);
  }

}
