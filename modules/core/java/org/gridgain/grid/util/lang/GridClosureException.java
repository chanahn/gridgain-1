/* 
 Copyright (C) GridGain Systems. All Rights Reserved.
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.grid.util.lang;

import org.gridgain.grid.*;

/**
 * This exception provides closures with facility to throw exceptions. Closures can't
 * throw checked exception and this class provides a standard idiom on how to wrap and pass an
 * exception up the call chain. It is also frequently used with {@link GridEither} to return
 * either wrapping exception or value from the closure.
 * @see GridEither
 * @see GridFunc#wrap(Throwable)
 */
public class GridClosureException extends GridRuntimeException {
    /**
     * Creates wrapper closure exception for given {@link GridException}.
     *
     * @param e Exception to wrap.
     */
    public GridClosureException(Throwable e) {
        super(e);
    }

    /**
     * Unwraps the original {@link Throwable} instance.
     *
     * @return The original {@link Throwable} instance.
     */
    public Throwable unwrap() {
        return getCause();
    }
}
