package nh.publy.backend.graphql;

import java.util.Base64;

public class NodeId {

  private NodeType nodeType;
  private Long id;

  public NodeId(String id) {
    try {
      String[] parts = decodeFromBase64(id).split(":");
      if (parts.length != 2) {
        throw new IllegalArgumentException(id);
      }

      this.nodeType = NodeType.valueOf(parts[0]);
      this.id = Long.parseLong(parts[1]);

    } catch (Exception ex) {
      throw new IllegalArgumentException(id);
    }
  }

  public NodeId(Long id, NodeType nodeType) {
    this.id = id;
    this.nodeType = nodeType;
  }

  private void expectType(NodeType expectedNodeType) {
    if (!this.nodeType.equals(expectedNodeType)) {
      throw new IllegalStateException(String.valueOf(this.id));
    }
  }

  /** If this NodeId points to a Story Node, return the id, otherwise throws exception */
  public Long getStoryId() {
    expectType(NodeType.story);
    return id;
  }

  /** If this NodeId points to a Member Node, return the id, otherwise throws exception */
  public Long getMemberId() {
    expectType(NodeType.member);
    return id;
  }

  /** If this NodeId points to a Comment Node, return the id, otherwise throws exception */
  public Long getCommentId() {
    expectType(NodeType.comment);
    return id;
  }

  public Long getId() {
    return id;
  }

  public NodeType getNodeType() {
    return nodeType;
  }

  public String toString() {
    return encodeToBase64(this.nodeType.name() + ":" + getId());
  }

  private static String decodeFromBase64(String base64String) {
    return new String(Base64.getDecoder().decode(base64String));
  }

  private static String encodeToBase64(String rawString) {
    return new String(Base64.getEncoder().encodeToString(rawString.getBytes()));
  }
}
