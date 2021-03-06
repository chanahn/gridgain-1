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

package org.gridgain.grid.ggfs.mapreduce.records;

import org.gridgain.grid.*;
import org.gridgain.grid.ggfs.*;
import org.gridgain.grid.ggfs.mapreduce.*;
import org.gridgain.grid.util.typedef.internal.*;

import java.io.*;

/**
 * Record resolver which adjusts records to fixed length. That is, start offset of the record is shifted to the
 * nearest position so that {@code newStart % length == 0}.
 */
public class GridGgfsFixedLengthRecordResolver implements GridGgfsRecordResolver, Externalizable {
    /** Record length. */
    private long recLen;

    /**
     * Empty constructor required for {@link Externalizable} support.
     */
    public GridGgfsFixedLengthRecordResolver() {
        // No-op.
    }

    /**
     * Creates fixed-length record resolver.
     *
     * @param recLen Record length.
     */
    public GridGgfsFixedLengthRecordResolver(long recLen) {
        this.recLen = recLen;
    }

    /** {@inheritDoc} */
    @Override public GridGgfsFileRange resolveRecords(GridGgfs ggfs, GridGgfsInputStream stream,
        GridGgfsFileRange suggestedRecord)
        throws GridException, IOException {
        long suggestedEnd = suggestedRecord.start() + suggestedRecord.length();

        long startRem = suggestedRecord.start() % recLen;
        long endRem = suggestedEnd % recLen;

        long start = Math.min(suggestedRecord.start() + (startRem != 0 ? (recLen - startRem) : 0),
            stream.fileInfo().length());
        long end = Math.min(suggestedEnd + (endRem != 0 ? (recLen - endRem) : 0), stream.fileInfo().length());

        assert end >= start;

        return start != end ? new GridGgfsFileRange(suggestedRecord.path(), start, end - start) : null;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(GridGgfsFixedLengthRecordResolver.class, this);
    }

    /** {@inheritDoc} */
    @Override public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(recLen);
    }

    /** {@inheritDoc} */
    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        recLen = in.readLong();
    }
}
