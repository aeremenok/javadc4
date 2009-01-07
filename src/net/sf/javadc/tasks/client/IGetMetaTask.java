/* 
 * Copyright (C) 2004 Timo Westkämper
 *
 * This program is free software;      you can redistribute it and/or modify it
 * under the terms of the   GNU General Public License as published by the Free
 * Software Foundation;    either version 2 of the License, or (at your option)
 * any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;   without even the implied warranty of MERCHANTABILITY or FIT-
 * NESS FOR A PARTICULAR PURPOSE.   See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package net.sf.javadc.tasks.client;

import net.sf.javadc.tasks.BaseClientTask;

/**
 * <p>
 * A $Search/$SR extension/modification that enables clients to identify files
 * by hash.
 * </p>
 * 
 * <p>
 * $GetMeta &lt;meta-type&gt;$&lt;extended-filename&gt;|
 * </p>
 * <p>
 * $Meta &lt;meta-type&gt;:&lt;meta-data&gt;$&lt;extended-filename&gt;|
 * </p>
 * 
 * <p>
 * extended-filename := &lt;meta-type&gt;:&lt;base32-encoded-hash&gt; |
 * &lt;filename&gt;
 * </p>
 * <p>
 * meta-type := TTH;&lt;hash-tree-depth-requested&gt;
 * </p>
 * 
 * <p>
 * Note: The answer will look like TTH;X: where X is the actual amount of levels
 * sent. Dont count on this being what you requested, and also dont count on the
 * returned data to adher to this or anything else. Also, validate the tree.
 * (this is just robustness tips) BCDC++ currently stores 9 levels of trees.
 * Search
 * </p>
 * 
 * <p>
 * the number 9 (the first available search-type) is used when searching by
 * hash. the search term must consist of
 * "TTH:&lt;base32encoded-tigertree-hash&gt;" SR (Search Result)
 * </p>
 * 
 * <p>
 * when the client knows the root hash of a file, it should not send HubName in
 * the $SR, but instead "TTH:<base32encoded-tigertree-hash>" (note that the
 * hubname field is still postceeded by the hubaddress+port)
 * </p>
 * 
 * @author Timo Westk�mper
 */
public class IGetMetaTask extends BaseClientTask {

    // private static final Category logger =
    // Logger.getLogger(IGetMetaTask.class);

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.javadc.tasks.BaseClientTask#runTaskTemplate()
     */
    protected void runTaskTemplate() {
        // TODO Auto-generated method stub

    }

}
