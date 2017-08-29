/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.util.Holders

class PostGroup extends Entry {

    String title
    Asset media
    List<Post> posts
    String rssUrl
    List<Post> publishedPosts
    String rssMetadata

    static String contentfulContentType = "post-group"

    List<Post> getPublishedPosts() {
        rssPosts + contentfulPosts
    }

    List<Post> getContentfulPosts() {
        posts.findAll {
            it && (!it.date || it.date < new Date())
        }
    }

    private List<Post> getRssPosts() {
        RssPostService rssPostService = Holders.grailsApplication.mainContext.getBean('rssPostService')
        rssPostService.fetch(this)
    }
}
