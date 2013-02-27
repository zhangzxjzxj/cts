/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
#ifndef CONTEXTSWITCHRENDERER_H
#define CONTEXTSWITCHRENDERER_H

#include <Renderer.h>

class ContextSwitchRenderer: public Renderer {
public:
    ContextSwitchRenderer(ANativeWindow* window, bool offscreen, int workload);
    virtual ~ContextSwitchRenderer() {};
    bool setUp();
    bool tearDown();
    bool draw();
private:
    GLuint mTextureUniformHandle;
    GLuint mPositionHandle;
    GLuint mTexCoordHandle;
    EGLContext* mContexts;
    GLuint* mTextureIds;
    GLuint* mFboIds;
    GLuint* mRboIds;
    GLuint* mCboIds;
    GLuint* mProgramIds;
    GLuint* mTextureUniformHandles;
    GLuint* mPositionHandles;
    GLuint* mTexCoordHandles;
};

#endif
