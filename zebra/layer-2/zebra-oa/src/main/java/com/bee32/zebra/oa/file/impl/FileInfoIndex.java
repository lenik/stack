package com.bee32.zebra.oa.file.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.bodz.bas.http.ctx.CurrentHttpService;
import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathArrival;
import net.bodz.bas.repr.path.PathDispatchException;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 描述文件、档案的相关信息。
 * 
 * @label 文件/档案
 * 
 * @rel tag/?tagv=5: 管理文件标签
 * @rel att/?schema=9: 管理文件属性
 * @rel org/: 管理企、事业组织
 * @rel person/: 管理联系人
 */
@ObjectType(FileInfo.class)
public class FileInfoIndex
        extends QuickIndex {

    public FileInfoIndex(IQueryable context) {
        super(context);
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        String token = tokens.peek();

        switch (token) {
        case "upload":
            UploadHandler uploadHandler = new UploadHandler();
            HttpServletRequest request = CurrentHttpService.getRequest();
            UploadResult result;
            try {
                result = uploadHandler.handlePostRequest(request);
            } catch (IOException e) {
                throw new PathDispatchException(e.getMessage(), e);
            }
            return PathArrival.shift(previous, result, tokens);
        }

        return super.dispatch(previous, tokens);
    }

}
