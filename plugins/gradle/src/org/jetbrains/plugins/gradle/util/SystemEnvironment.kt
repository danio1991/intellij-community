// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.plugins.gradle.util

import com.intellij.util.EnvironmentUtil

class SystemEnvironment : Environment {

  override fun getProperty(name: String): String? {
    return System.getProperty(name)
  }

  override fun getVariable(name: String): String? {
    return EnvironmentUtil.getValue(name)
  }
}